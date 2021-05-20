import Axios from 'axios'
import {
  AUTH_USER,
  LOGIN_USER,
} from './types'

export function auth(email) {
  const request = Axios.get(`/member/${email}`)
    .then(res => res.data)

  return {
    type: AUTH_USER,
    payload: request
  }
}

export function loginUser(variables) {
  const request = Axios.post('/auth/login', variables)
    .then(res => res)

  return {
    type: LOGIN_USER,
    payload: request
  }
}
