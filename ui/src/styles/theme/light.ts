import {DefaultTheme} from "styled-components";

const light: DefaultTheme = {
    background: {
        color: '#212121',
    },
    font: {
        color: '#e3e3e4',
    },
    primary: {
        background: {
            color: '#045136',
        },
        font: {
            color: '#e3e3e4',
        }
    },
    secondary: {
        background: {
            color: '#b3b3b3',
        },
        font: {
            color: '#212121',
        }
    },
}

export default light;


/*
Opacity %   255 Step        2 digit HEX prefix
    0%          0.00            00
    5%          12.75           0C
    10%         25.50           19
    15%         38.25           26
    20%         51.00           33
    25%         63.75           3F
    30%         76.50           4C
    35%         89.25           59
    40%         102.00          66
    45%         114.75          72
    50%         127.50          7F
    55%         140.25          8C
    60%         153.00          99
    65%         165.75          A5
    70%         178.50          B2
    75%         191.25          BF
    80%         204.00          CC
    85%         216.75          D8
    90%         229.50          E5
    95%         242.25          F2
    100%        255.00          FF
*/