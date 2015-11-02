#Starting architecture

The architecture provided in ExampleFuncsPlayer is actually awful, it was made just to show how functions work. 
Between this example structure and ExampleFuncsPlayer's code, you should have a good time.

We basically have split all our logic up so that each unit type gets its own class. This means rather than having 1 5000 line class, we'll have 20 250 line classes.
That sounds like a lot, but you'll probably only ever be editing 3 or 4 at most, meaning you don't even have to *look* at all that other code. It's nice, trust me.