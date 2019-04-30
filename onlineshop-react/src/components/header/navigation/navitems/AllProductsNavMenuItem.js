import React from 'react'
import { NavLink } from 'react-router-dom'

export default function AllProductsNavMenuItem() {

  return (
        <li className="nav-item">
            <NavLink to="/" 
                className="nav-link" 
                activeClassName="active"  
                exact
                >All Products</NavLink> 
        </li>
  )
}
