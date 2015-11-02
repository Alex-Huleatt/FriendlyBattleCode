#BattleCode

This little guide is **not** meant to replace the [game specs](https://github.com/bovard/battlecode-server-2014/blob/325ac6f5a9b3333f439ed3d30aec33209746e982/specs.md).
Read that too as needed.
Also, this shouldn't replace ExampleFuncsPlayer that is provided. It shows off a lot about how functions work, and is really neat.

##RobotPlayer

This is the only class that you are *required* to make. It is how the main program finds your code.
You will need code like the following.

	public class RobotPlayer {

		public static void run(RobotController rc) {
			//Your code goes here.
		}
	}

##RobotController
To *control* a robot, you use the *RobotController* class that is passed in.

You have access to functions like:

* move(Direction d), which takes a direction and moves your robot there
* canMove(Direction d), which lets you know if your robot is capable of moving in some direction
* isActive(), which lets you know if your robot is currently able to perform an action
* getLocation(), which gives you an object representing your position
* senseNearbyGameObjects, which has a bunch of possible inputs, but returns an array of "GameObjects", which are probably robots.

There are a ton of functions, all of which can be seen in the [API documentation](http://bovard.github.io/bcode2013-scaffold/).

##Error Handling

If a robot throws an error, it dies.
What we can do to avoid this is "catch" errors before they get passed to the main program.
The whole concept of error throwing and catching is not terribly relevant right here, so here is the solution.


	public class RobotPlayer {

		public static void run(RobotController rc) {
			while (1) {
				try {
					//your code here
				} catch (Exception e) {
					//print debug info
				}
			}
		}
	}

Now every function we make just has to tell Java that it's possible that this function *could* throw and error.
We do this like so:

	public static int foo() throws Exception {
		//something that throws an error here
	}

Normally you would specify what *type* of error gets thrown, but there is probably more than one and then it looks ugly and is tedious to write. (Please don't think this is good programming)

##Communication

You cannot share variables between units, and you cannot directly communicate with eachother. Instead, there is a global list of integers (about ~65000 long) that *everyone* can read and write to. 

	rc.broadcast(1,1);

would set the first integer in the list to the value 1.

	rc.readBroadcast(1);

would then return a 1, provided that the previous robot's turn has ended, and no one else changed that value in between their turns.

Units' turns happen in a weird order with only one important piece of info: HQ's go first.
This is really useful because it means that on each turn, if they broadcast something, all the other units will see it during the same turn.

It is also important to note that the enemy can read, but more importantly write over, your data.
You probably want some method to ensure that communications you read came from someone on your team.

##Bytecode

Bytecode is the language that Java gets translated to, so that computers can run it. 
Each line of Bytecode is a single instruction like "add", "multiply", "store some value in a variable".
Each turn a Robot can use 10,000 Bytecode. After that, he just stops wherever he is in his code, and picks up where he left off next round.
You don't want to go over the limit. Your code needs to be pretty fast.

Iterating through large arrays is expensive, try not to do that, or do it sparingly. This is probably the biggest source of slowness.

In this version of BattleCode, you have a resource that is somewhat dependent on your Bytecode, so there is an extra incentive to have really, really fast code.


##General Gist of Gameplay

* You get an HQ. 
* Your HQ spawns units. 
* There is a cooldown on spawning units.

* Soldiers (the units spawned by the HQ) do damage to something just by being near it.

* There are 2 types of things that can be on the ground.
	* Mines, which can be yours (don't do damage to you), enemies', or neutral (which hurt everyone).
	* Encampment squares where you can build structures.

* There are 2 important resources you need to pay attention to.
	* Power, which is required to *do* anything: build structures, move units, whatever.
		* This is one value shared by your entire team.
		* If you don't have enough, your robots start taking damage.
	* Energon, which is *not* shared and is essentially how much health a robot has.


