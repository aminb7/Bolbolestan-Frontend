class NewpasswordPart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {password : ''};
        this.sendPassword = this.sendPassword.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
    }

    sendPassword(event) {
        event.preventDefault();
        const requestOptions = {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + window.location.href.substr(29)
            },
        };
        fetch('new_pass?password=' + this.state.password, requestOptions)
            .then(response => response.text())
            .then((data) => {
                console.log(data);
                if (data == "true") alert('رمز عبور با موفقیت تغییر یافت.');
                else alert('ایمیل یا رمز عبور نامعتبر است!');
            });
    }

    handlePassword(event) {
        this.setState(prevState => ({password: event.target.value}));
    }

    render() {
        return (
            <div className="container login_text">
                <h1>تغییر رمز عبور</h1>

                <form onSubmit={this.sendPassword} className="login_form">
                    <div className="login_img_container">
                        <img src="../images/icons/login_avatar.png" alt="Avatar" className="login_avatar"/>
                    </div>
                    <div className="container login_container">
                        <label for="password"><b>رمز عبور</b></label>
                        <input type="password" placeholder="رمز خود را وارد کنید." className="login_input" onChange={this.handlePassword} required/>
                        <button type="submit" className="login_button">تغییر رمز عبور</button>
                    </div>
                </form>
                <div className="container login_container">
                    <a href="/" className="login_button">صفحه ورود</a>
                </div>
            </div>
        );
    }
}

class NewpasswordPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <NewpasswordPart />
                <BolbolestanFooter />
            </div>
        );
    }
}