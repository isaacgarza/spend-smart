import React, { Component } from 'react';
import './App.css';
import Transactions from "./components/Transaction";
import SignUp from "./components/SignUp";
import Goals from "./components/Goals";
import SignIn from "./components/SignIn";

class App extends Component {
  constructor() {
    super();
    this.state = {
      loggedIn: null
    }
  }
  render() {
    return (
      <div className="App">
        {!this.state.loggedIn &&
          <SignUp/>
        }
        {this.state.loggedIn &&
          <Transactions/>
        }
        {!this.state.loggedIn &&
          <SignIn/>
        }
        <Goals/>
      </div>
    );
  }
}

export default App;
