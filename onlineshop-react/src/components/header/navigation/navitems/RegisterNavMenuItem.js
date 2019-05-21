import React from 'react'
import { NavLink } from 'react-router-dom'


export default function RegisterNavMenuItem() {
  
  return (
        <li className="nav-item">
            <NavLink to="/register"
            className="nav-link" 
            activeClassName="active"
        
            ><i className="fas fa-user-plus text-custom my-auto"></i>
            Register</NavLink>
        </li>
  )
}