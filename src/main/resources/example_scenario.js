/*
 * An example configuration file for SIMLet using the example models.
 */

var Scheduler = Java.type("fi.paivola.simlet.time.Scheduler");
var Time = Java.type("fi.paivola.simlet.time.Time");
var Unit = Java.type("fi.paivola.simlet.time.Unit");
var Parameter = Java.type("fi.paivola.simlet.sampler.Parameter");
var Pos = Java.type("fi.paivola.simlet.misc.Pos");
var DumbSampler = Java.type("fi.paivola.simlet.sampler.DumbSampler");
var Configure = Java.type("fi.paivola.simlet.runner.Configure");

var Town = Java.type("fi.paivola.simlet.model.example.Town");
var Field = Java.type("fi.paivola.simlet.model.example.Field");
var Road = Java.type("fi.paivola.simlet.model.example.Road");

function getConfiguration() {
    return new Configure({
        parameters: [
            new Parameter("wheat", "optimal wheat", 3, 9),
            new Parameter("hunger", "", 0.1, 2),
            new Parameter("robberies", "percent of lost shipments", 0.0, 0.2)
        ],
        samples: 6,
        sampler: new DumbSampler(),
        runs: 4,
        ends: new Time(52, Unit.WEEK),
        plan: function (scheduler, parameters) {
            var town = new Town("Town 1", new Pos(32, 32.1), 30.0, {
                "hunger": parameters["hunger"]
            });

            var field = new Field("Field 1", new Pos(32.1, 32), 15.0, {
                "wheat": parameters["wheat"]
            });

            var road = new Road("Road 1", town, field, {
                "robberies": parameters["robberies"]
            });

            function register(a, b) {
                for(c in b) {
                    b[c].registerCallbacks(a);
                }
            }

            register(scheduler, [
                town, field, road
            ]);
        }
    });
}