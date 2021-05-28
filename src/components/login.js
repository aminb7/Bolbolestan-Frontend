import React from "react";
import ReactDOM from "react-dom";
import HomePage from "./home"
import SignupPage from "./signup";
import ForgetPage from "./forget";
import BolbolestanFooter from "./footer";

class LoginPart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {email : '', password : ''};
        this.sendLogin = this.sendLogin.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handleSignup = this.handleSignup.bind(this);
        this.handleForget = this.handleForget.bind(this);
    }

    sendLogin(event) {
        event.preventDefault();
        var params = {
            "email": this.state.email,
            "password": this.state.password
        };
        var queryString = Object.keys(params).map(function(key) {
            return key + '=' + params[key]
        }).join('&');

        fetch('http://localhost:8080/login?' + queryString)
            .then(response => response.json())
            .then((data) => {
                if (Object.keys(data)[0] == "key") {
                    localStorage.setItem("token", data["key"])
                    ReactDOM.render(<HomePage />, document.getElementById('app'));
                }
                else alert('ایمیل یا رمز عبور نامعتبر است!');
            });
    }

    handlePassword(event) {
        this.setState(prevState => ({password: event.target.value}));
    }

    handleEmail(event) {
        this.setState(prevState => ({email: event.target.value}));
    }

    handleSignup(event) {
        ReactDOM.render(<SignupPage />, document.getElementById('app'));
    }

    handleForget(event) {
        ReactDOM.render(<ForgetPage />, document.getElementById('app'));
    }

    render() {
        return (
            <div className="container login_text">
                <h1>صفحه‌ی ورود</h1>

                <form onSubmit={this.sendLogin} className="login_form">
                    <div className="login_img_container">
                        <img src="../assets/icons/login_avatar.png" alt="Avatar" className="login_avatar"/>
                    </div>

                    <div className="container login_container">
                        <label for="email"><b>ایمیل</b></label>
                        <input type="text" placeholder="ایمیل خود را وارد کنید." className="login_input" onChange={this.handleEmail} required/>

                        <br/>

                        <label for="password"><b>رمز عبور</b></label>
                        <input type="password" placeholder="رمز خود را وارد کنید." className="login_input" onChange={this.handlePassword} required/>
                        <button type="submit" className="login_button">ورود</button>

                        <button type="button" onClick={this.handleSignup} className="login_button">صفحه ثبت نام</button>
                        <button type="button" onClick={this.handleForget} className="login_button">فراموشی رمز عبور</button>
                    </div>
                </form>
            </div>
        );
    }
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        if (localStorage.hasOwnProperty('token'))
            return ReactDOM.render(<HomePage />, document.getElementById('app'));

        return (
            <div>
                <LoginPart />
                <BolbolestanFooter />
            </div>
        );
    }
}

export default LoginPage;