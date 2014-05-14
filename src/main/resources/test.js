var Parameter = Java.type("fi.paivola.simlet.sampler.Parameter");
var DumbSampler = Java.type("fi.paivola.simlet.sampler.DumbSampler");
var Town = Java.type("fi.paivola.simlet.model.example.Town");
var Field = Java.type("fi.paivola.simlet.model.example.Field");
var Road = Java.type("fi.paivola.simlet.model.example.Road");

configure({
    parameters: [
        new Parameter("wheat", "optimal wheat", 3, 9),
        new Parameter("hunger", "", 0.1, 2)
    ],
    samples: 5,
    sampler: new DumbSampler(),
    runs: 20,
    plan: function(m) {
        var town = new Town("Town 1",
                           {"pos": Pos(32, 32.1),
                            "size": 30.0,
                            "hunger": m.parameters["hunger"]});

        var field = new Field("Field 1",
                             {"pos": Pos(32.1, 32),
                              "size": 15.0,
                              "wheat": m.parameters["wheat"]});

        var road = new Road("Road 1",
                             town,
                             field,
                           {"robberies": 0.02});

        m.registerAll(town, field, road);
    })
});
