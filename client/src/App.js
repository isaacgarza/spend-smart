import React, { Component } from 'react';
import './App.css';
import Transactions from "./components/Transactions";
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
        <SignUp/>
        <Transactions/>
        <Goals/>
      </div>
    );
  }
}

export default App;
