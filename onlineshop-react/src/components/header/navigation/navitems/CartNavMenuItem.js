import React from 'react'
import '../../../../styles/custom.css';
import {NavLink} from 'react-router-dom'

export default function CartNavMenuItem() {
    return (
        <li className="nav-item">
            <NavLink to="/cart"
                     className="nav-link"
                     activeClassName="active"
            >
                <i className="fas fa-shopping-cart text-custom"/>
            </NavLink>
        </li>
    )
}
