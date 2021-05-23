import React from 'react';
import './components/app.css';
import Historic from './components/historic';
import RealTime from './components/realTime';
import Front    from './components/front';
import { Route, Link } from "react-router-dom";


function App(){
    return(
        <div clasName="App">
            <Route exact path="/" component={Front}/>
            <Route exact path="/historic" component={Historic}/>
            <Route exact path="/realTime" component={RealTime}/>

        </div>
    )
}

export default App; //expor to get access in index.js