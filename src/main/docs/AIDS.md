# Assembly-Inspired Diction System:

id | unit | AIDS code | COMMAND (param) | description
---|---|---|---|---
000 | | STP | E-STOP | runtime command, triggers emergency stop
010 | dam | FWD | MOVE FORWARD (x) |
011 | dam | BCK | MOVE BACKWARD (x) |
012 | deg | LFT | TURN LEFT (x) |
013 | deg | RGT | TURN RIGHT (x)
020 | | LHT | LIGHT (x) | sets light configuration to preset x
021 | | LTP | LIGHT (program??) | maybe we can get more specific with lights idk
069 | | FLW | FOLLOW ME | follows the april tag (safe, trust me)
100 | | DG0 | DIGIT 0
101 | | DG1 | DIGIT 1
102 | | DG2 | DIGIT 2
103 | | DG3 | DIGIT 3
104 | | DG4 | DIGIT 4
105 | | DG5 | DIGIT 5
106 | | DG6 | DIGIT 6
107 | | DG7 | DIGIT 7
108 | | DG8 | DIGIT 8
109 | | DG9 | DIGIT 9
587 | | EOF | END OF FILE | run specified commands <!-- last april tag -->