import React from 'react';
import './app.css';
import Axios from 'axios';

function Historic(){
    const dbInfo = async() => {
        const{status, data} = await Axios.post("http://localhost:3001/api/getAll");
        console.log(status);
        console.log(data);
    };
    dbInfo();
    return(
        <div>
            
            Historic
        </div>
    )
}

export default Historic; //expor to get access in index.js