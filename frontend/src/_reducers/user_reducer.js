import {
  AUTH_USER,
  LOGIN_USER
} from '../_actions/types'

export default function UserReducer(state={}, action) {
  switch (action.type) {
    case AUTH_USER:
      return {...state, userData: action.payload}
    case LOGIN_USER:
      return {...state, loginSuccess: action.payload}
    default:
      return state
  }
}
