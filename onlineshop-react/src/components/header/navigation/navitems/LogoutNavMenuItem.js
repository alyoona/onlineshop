import React from 'react'
import '../../../../styles/custom.css';

export default function LogoutNavMenuItem() {
  return (
    <li className="nav-item">
        <div className="nav-link">
            <form action="/logout" method="post" className="form-inline">
                <button className="btn btn-sm bg-transparent text-custom mb-0" title="Logout" data-toggle="tooltip">
                    <i className="fas fa-sign-out-alt text-custom "></i>
                </button>
            </form>
        </div>
    </li>
  )
}
