import React, {Component} from 'react'
import Promotion from './promotion/Promotion';
import NanvigationMenu from './navigation/NanvigationMenu';

class Header extends Component {
    render() {
        return (
            <header>
                <Promotion />
                <NanvigationMenu />
            </header>
        )
    }
}

export default Header;
