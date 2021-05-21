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
              const date = new Date()
              const expTime = parseInt(cookies.tl_exp)
              console.log(typeof(date.getTime()));

              const tokens = {
                "accessToken": cookies.tl_token,
                "refreshToken": cookies.tl_re
              }

              if(date.getTime() > expTime) {
                Axios.post("/auth/reissue", tokens)
                  .then(res => {
                    setCookie("tl_token", res.data.accessToken)
                    setCookie("tl_exp", res.data.accessTokenExpiresIn)
                    setCookie("tl_re", res.data.refreshToken)
                  })
                }

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
