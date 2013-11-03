#!/bin/bash

URL="http://localhost:8081"
EXPECTED_HIGHSCORE="100=100,99=99,98=98,97=97,96=96,95=95,94=94,93=93,92=92,91=91,90=90,89=89,88=88,87=87,86=86"

for i in {1..100}
do
    session=`curl -s $URL/$i/login`
    curl -s -d $i $URL/1/score?sessionkey=$session
done

highscores=`curl -s $URL/1/highscorelist`
if [ $highscores != $EXPECTED_HIGHSCORE ]; then
    echo "ERROR: Highscores did not match: $highscores != $EXPECTED_HIGHSCORE"
    exit -1
fi

echo "Tests passed!"