#!/bin/sh
#GET movies in given time interval
curl "localhost:8080/screenings?from=2020-01-20T14:00:23.445898400%2B01:00&to=2020-01-28T18:08:23.445898400%2B01:00"
#GET info about one screening
curl localhost:8080/screenings/1
#POST booking
curl -d '{"name":"Bartek", "surname":"Rzszkiewicz", "screeningId": 1, "ticketDtos": [{"row":1, "place":2, "type": "ADULT"}]}' -H "Content-Type: application/json" -X POST http://localhost:8080/bookings
#GET info about screening that we booked
curl localhost:8080/screenings/1
