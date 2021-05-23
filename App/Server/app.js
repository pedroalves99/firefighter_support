const express = require('express');
const Influx = require('influx');
const {Kafka} = require('kafkajs');

app = express();

const db = new Influx.InfluxDB('http://user:pass@localhost:8086/firefighters');

const kafka = new Kafka({
    clientID: 'consumerJS',
    brokers:   ['localhost:9092']
});

const consumer = kafka.consumer({groupId: 'consumer-group'});
const topic    = 'SensorData';

const run  = async() =>{
    await consumer.connect();

    await consumer.subscribe({topic});

    await consumer.run({
        eachMessage: async({ topic, partition, message})=>{
            console.log({
                partition,
                offset: message.offset,
                value: message.value.toString(),
            })
        }
    })

}

app.post("/api/getAll", (req,res) => {
    console.log("tu madre");
    db.query('select * from SensorData').then(results => {
        console.log(results)
    })

});

app.post("/api/realTime", (req,res) => {
    run();
});

app.listen(3001, () =>{
    console.log("running on port 3001");
});
