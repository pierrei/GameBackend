#!/bin/bash

URL="http://localhost:8081"
EXPECTED_HIGHSCORE="40=3532,30=1950"

session1=`curl -s $URL/30/login`
session2=`curl -s $URL/40/login`
if [ -z "$session1" ]; then
    echo "ERROR: Could not get session"
    exit -1
fi

curl -s -d 1950 $URL/1/score?sessionkey=$session1
curl -s -d 3532 $URL/1/score?sessionkey=$session2

highscores=`curl -s $URL/1/highscorelist`
if [ $highscores != $EXPECTED_HIGHSCORE ]; then
    echo "ERROR: Highscores did not match: $highscores != $EXPECTED_HIGHSCORE"
    exit -1
fi

echo "Tests passed!"