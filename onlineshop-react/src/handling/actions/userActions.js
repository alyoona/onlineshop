import axios from 'axios';
import {  
    AUTHENTICATED,
    LOGOUT
 } from './types';
 import { apiUrl } from '../../util/network';

export const login = (user, history) => async dispatch => {
    axios.defaults.withCredentials = true;
        try {
            const response = await axios.post(apiUrl('/login'),
                                                getFormData(user),
                                                { headers: {'Content-Type': 'multipart/form-data'} }
                                            );
            
            if(response.status === 200) {
                console.log("Login successfull");
                dispatch({
                    type: AUTHENTICATED,
                    payload: {username: user.username, role: response.data, loggedIn: true}
                });
                
                history.push("/");
            } 
           
 
        } catch (error) {
            console.log("login errors---", error);
        }     
         
};

export const register = (user, history) => async dispatch => {
    axios.defaults.withCredentials = true;
    try {
        const response = await axios.post(apiUrl("/register"), getFormData(user), { headers: {'Content-Type': 'multipart/form-data'}});
        console.log("register response---", response);
        if(response.status === 201) {
            console.log("Register successfull");
            dispatch({
                type: AUTHENTICATED,
                payload: {username: user.username, role: response.data, loggedIn: true}
            });
            
            history.push("/");
        }
    } catch (error) {
        console.log("register errors---",error);
    }

}
 
export const logout = () => async dispatch => {

    axios.defaults.withCredentials = true;
        try {
            const response = await axios.post(apiUrl('/logout'));
            console.log(response);
            if(response.status === 200) {
                console.log("Logout successfull");
                dispatch({
                    type: LOGOUT,
                    payload: {username: "", role: "", loggedIn: false}
                });
            } 
           
 
        } catch (error) {
            console.log(error);
        }  
};

const getFormData = (user) => {
    let form = new FormData();
    form.set("username", user.username);
    form.set("password", user.password);
    return form;
}

