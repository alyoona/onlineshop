import React, {Component} from 'react'
import Promotion from './promotion/Promotion';
import NavigationMenu from './navigation/NavigationMenu';

class Header extends Component {
    render() {
        return (
            <header>
                <Promotion />
                <NavigationMenu />
            </header>
        )
    }
}

export default Header;