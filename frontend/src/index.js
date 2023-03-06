import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './app/App';
import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter as Router} from 'react-router-dom';
import './fonts/Humane-SemiBold.ttf';

ReactDOM.render(
    <Router>
        <App/>
    </Router>,
    document.getElementById('root')
);

registerServiceWorker();
