import React from 'react';
import ReactDOM from 'react-dom';
import LoginPage from './components/login.js'
import NewpasswordPage from './components/newpassword'

const url = new URL(window.location.href);
const token = url.searchParams.get('new_password');

if (token == null)
    ReactDOM.render(<LoginPage />, document.getElementById('app'));
else
    ReactDOM.render(<NewpasswordPage token={token} />, document.getElementById('app'));