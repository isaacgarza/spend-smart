import React from "react";
import { Link } from "react-router-dom";
import { Navbar, Nav } from "react-bootstrap";

export default function NavHeader() {
  return (
    <Navbar
      collapseOnSelect="collapseOnSelect"
      expand="lg"
      bg="dark"
      variant="dark"
    >
      <Navbar.Brand>
        <Link to="/">SPEND SMART</Link>
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link>
            <Link to="/signin">Sign In</Link>
          </Nav.Link>
          <Nav.Link>
            <Link to="/signup">Sign Up</Link>
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}
