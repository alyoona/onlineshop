import React from 'react'
import {NavLink} from 'react-router-dom'


export default function AddProductNavMenuItem() {

    return (
        <li className="nav-item">
            <NavLink to="/products/add"
                     className="nav-link"
                     activeClassName="active"

            >Add Product</NavLink>
        </li>
    )
}
