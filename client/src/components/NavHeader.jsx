import React from "react";
import { Link } from "react-router-dom";
import { Navbar } from "react-bootstrap";
import styled from 'styled-components';

const NavContainer = styled.div`
  .title {
    color: white;
  }
  .links {
    padding-left: 20px;
  }
`;
export default function NavHeader() {
  return (
    <NavContainer>
      <Navbar
        collapseOnSelect="collapseOnSelect"
        expand="lg"
        bg="dark"
        variant="dark"
      >
        <Navbar.Brand >
          <Link className="title" to="/">SPEND SMART</Link>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse className="justify-content-end" id="responsive-navbar-nav">
            <Navbar.Text className="links">
              <Link to="/sign-in">Sign In</Link>
            </Navbar.Text>
            <Navbar.Text className="links">
              <Link to="/sign-up">Sign Up</Link>
            </Navbar.Text>
        </Navbar.Collapse>
      </Navbar>
    </NavContainer>
  );
}
