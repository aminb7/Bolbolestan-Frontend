import React from 'react';
import ReactDOM from 'react-dom';
import '../styles/commons.css'
import '../styles/login.css'
import LoginPage from "./login";
import BolbolestanFooter from "./footer";

class SignupPart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {id : ''};
        this.handleLogin = this.handleLogin.bind(this);
        this.sendSignup = this.sendSignup.bind(this);
        this.handleId = this.handleId.bind(this);
        this.handleName = this.handleName.bind(this);
        this.handleSecondName = this.handleSecondName.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
        this.handleBirthDate = this.handleBirthDate.bind(this);
        this.handleField = this.handleField.bind(this);
        this.handleFaculty = this.handleFaculty.bind(this);
        this.handleLevel = this.handleLevel.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
    }

    handleLogin(event) {
        ReactDOM.render(<LoginPage />, document.getElementById('app'));
    }

    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    validateId(id) {
        return !isNaN(id);
    }

    inputFormatsAreCorrect() {
        return this.validateEmail(this.state.email) && this.validateId(this.state.id);
    }

    sendSignup(event) {
        event.preventDefault();
        var params = {
            "id": this.state.id,
            "name": this.state.name,
            "secondName": this.state.secondName,
            "email": this.state.email,
            "birthDate": this.state.birthDate,
            "field": this.state.field,
            "faculty": this.state.faculty,
            "level": this.state.level,
            "password": this.state.password
        };

        var queryString = Object.keys(params).map(function(key) {
            return key + '=' + params[key]
        }).join('&');

        fetch('http://87.247.185.122:31919/signup?' + queryString)
            .then(response => response.json())
            .then((data) => {
                if (data == false) alert('?????????? ?? ???? ?????????? ???????????????? ???????????? ??????!');
                else if (!this.inputFormatsAreCorrect()) alert('???????? ?????????? ???? ???????? ????????!');
                else ReactDOM.render(<LoginPage />, document.getElementById('app'));
            });
    }

    handleId(event) {
        this.setState(prevState => ({id: event.target.value}));
    }

    handleName(event) {
        this.setState(prevState => ({name: event.target.value}));
    }

    handleSecondName(event) {
        this.setState(prevState => ({secondName: event.target.value}));
    }

    handleEmail(event){
        this.setState(prevState => ({email: event.target.value}));
    }

    handleBirthDate(event){
        this.setState(prevState => ({birthDate: event.target.value}));
    }

    handleField(event){
        this.setState(prevState => ({field: event.target.value}));
    }

    handleFaculty(event){
        this.setState(prevState => ({faculty: event.target.value}));
    }

    handleLevel(event){
        this.setState(prevState => ({level: event.target.value}));
    }

    handlePassword(event){
        this.setState(prevState => ({password: event.target.value}));
    }

    render() {
        return (
            <div className="container login_text">
                <h1>????????????? ?????? ??????</h1>

                <form onSubmit={this.sendSignup} className="login_form">
                    <div className="login_img_container">
                        <img src="../assets/icons/login_avatar.png" alt="Avatar" className="login_avatar"/>
                    </div>

                    <div className="container login_container">
                        <label htmlFor="id"><b>?????????? ????????????????</b></label>
                        <input type="text" placeholder="?????????? ???????????????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleId} required/>
                        <br/>

                        <label htmlFor="name"><b>??????</b></label>
                        <input type="text" placeholder="?????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleName} required/>
                        <br/>

                        <label htmlFor="secondName"><b>?????? ????????????????</b></label>
                        <input type="text" placeholder="?????? ???????????????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleSecondName} required/>
                        <br/>

                        <label for="email"><b>??????????</b></label>
                        <input type="text" placeholder="?????????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleEmail} required/>
                        <br/>

                        <label htmlFor="birthDate"><b>?????????? ????????</b></label>
                        <input type="text" placeholder="?????????? ???????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleBirthDate} required/>
                        <br/>

                        <label htmlFor="field"><b>????????</b></label>
                        <input type="text" placeholder="???????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleField} required/>
                        <br/>

                        <label htmlFor="faculty"><b>??????????????</b></label>
                        <input type="text" placeholder="?????????????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleFaculty} required/>
                        <br/>

                        <label htmlFor="level"><b>???????? ????????????</b></label>
                        <input type="text" placeholder="???????? ???????????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handleLevel} required/>
                        <br/>

                        <label for="password"><b>?????? ????????</b></label>
                        <input type="password" placeholder="?????? ?????? ???? ???????? ????????." className="login_input"
                               onChange={this.handlePassword} required/>

                        <button type="submit" className="login_button">?????? ??????</button>
                        <button type="button" onClick={this.handleLogin} className="login_button">???????? ????????</button>
                    </div>
                </form>
            </div>
        );
    }
}

class SignupPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <SignupPart />
                <BolbolestanFooter />
            </div>
        );
    }
}

export default SignupPage;