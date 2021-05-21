import React, { useEffect } from "react"
import { auth } from "../_actions/user_actions"
import { useSelector, useDispatch } from "react-redux"
import { useCookies } from "react-cookie"
import Axios from "axios"

export default function (SpecificComponent, option, adminRoute = null) {
    function AuthenticationCheck(props) {

      const [cookies, setCookie] = useCookies([])
      let user = useSelector(state => state.user)
      const dispatch = useDispatch()

      useEffect(() => {

          if (!cookies.tl_e) {
            if (option) {
              props.history.push("/login")
            }
            } else {
              // member 정보 가져오기 위해 header에 token 담아서 넘김
              Axios.defaults.headers.common['Authorization'] = `Bearer ${cookies.tl_token}`
              dispatch(auth(cookies.tl_e))
            }

      }, [cookies])

      return (
        <SpecificComponent {...props} user={user} />
      )
    }
  return AuthenticationCheck
}
