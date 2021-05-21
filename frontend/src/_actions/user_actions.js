import Axios from 'axios'
import {
  AUTH_USER,
} from './types'

export function auth(email) {
  const request = Axios.get(`/member/${email}`)
    .then(res => res.data)

  return {
    type: AUTH_USER,
    payload: request
  }
}
