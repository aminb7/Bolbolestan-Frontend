import React from 'react';
import '../styles/commons.css'
import '../styles/login.css'
import BolbolestanFooter from "./footer";

class ForgetPart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {email : ''};
        this.sendEmail = this.sendEmail.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
    }

    sendEmail(event) {
        event.preventDefault();
        fetch('http://87.247.185.122:31919/send_mail?email=' + this.state.email)
            .then(response => response.text())
            .then((data) => {
                alert('درخواست شما با موفقیت ارسال شد.');
            });
    }

    handleEmail(event) {
        this.setState(prevState => ({email: event.target.value}));
    }

    render() {
        return (
            <div className="container login_text">
                <h1>فراموشی رمز عبور</h1>

                <form onSubmit={this.sendEmail} className="login_form">
                    <div className="login_img_container">
                        <img src="../assets/icons/login_avatar.png" alt="Avatar" className="login_avatar"/>
                    </div>
                    <div className="container login_container">
                        <label for="email"><b>ایمیل</b></label>
                        <input type="email" placeholder="ایمیل خود را وارد کنید." className="login_input" onChange={this.handleEmail} required/>
                        <button type="submit" className="login_button">ارسال</button>
                    </div>
                </form>
            </div>
        );
    }
}

class ForgetPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <ForgetPart />
                <BolbolestanFooter />
            </div>
        );
    }
}

export default ForgetPage;