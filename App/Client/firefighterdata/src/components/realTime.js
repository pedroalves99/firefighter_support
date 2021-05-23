import React from 'react';
import './app.css';
import Axios from 'axios';

function RealTime(){
    const realTIme = async() => {
        const{status, data} = await Axios.post("http://localhost:3001/api/realTIme");
        console.log(status);
        console.log(data);
    };
    realTIme();
    return(
        <div>
            RealTime
        </div>
    )
}

export default RealTime; //expor to get access in index.js