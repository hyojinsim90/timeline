import {
  AUTH_USER,
} from '../_actions/types'

export default function UserReducer(state={}, action) {
  switch (action.type) {
    case AUTH_USER:
      return {...state, userData: { email: action.payload.email, nickname: action.payload.nickname } }
    default:
      return state
  }
}
