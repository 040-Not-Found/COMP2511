# Rationale - Lab 03 Cars - Your Name

Make sure you articulate each of your decisions in terms of 
* What you chose to do
* Why you chose to do it - here it is good to draw on course concepts

* I have done XXX because XXX

e.g. I have used an interface over an abstract class because ...,
there is an aggregation between X and Y because ...

1. Planes has passenger, so I created a class of passenger. Passengers will have name, flightId and passportId. However, the Ids are important, so I set the getter for the Ids to protected.
2. The Plane can add passengers and return a list of exist passenger.
3. Planes also needs Engine, therefore, Engine is aggregated to Plane.
4. I created a inferface call <<Falyable>>, so that the FlyingCar can implements that interface, and TimeTravellingFlyingCar can implements both <<Flyabe>> and <<TimeTravelling>> interfaces.
5. Owner can have zero or more cars, therefore, car is associated to Owner.
6. NuclerEngine have nuclearValue and this can propel at their nuclear energy value. So I added a boolean method to see if the engine is propelling.