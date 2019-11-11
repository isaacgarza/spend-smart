import React, { Component } from "react";
import "./SignIn.css";
import { Link, Redirect } from "react-router-dom";
import { GOOGLE_AUTH_URL } from "../../constants";
import googleLogo from "../../img/google-logo.png";

class SignIn extends Component {
  render() {
    if (this.props.authenticated) {
      return (
        <Redirect
          to={{
            pathname: "/",
            state: { from: this.props.location }
          }}
        />
      );
    }

    return (
      <div className="sign-in-container">
        <div className="sign-in-content">
          <h1 className="sign-in-title">Sign in with Spend Smart</h1>
          <SocialSignIn />
          <div className="or-separator">
            <span className="or-text">OR</span>
          </div>
          <span className="sign-up-link">
            Don't have an account? <Link to="/sign-up">Sign up!</Link>
          </span>
        </div>
      </div>
    );
  }
}

class SocialSignIn extends Component {
  render() {
    return (
      <div className="social-sign-in">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
          <img src={googleLogo} alt="Google" /> Sign in with Google
        </a>
      </div>
    );
  }
}

export default SignIn;
