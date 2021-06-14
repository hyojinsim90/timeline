import React, { useState, useEffect } from "react"
import styled from "styled-components"
import ViewsTop from "./Sections/ViewsTop"
import Axios from "axios"

const MainDiv = styled.div`
  padding: 3rem 0;
`

const Main = () => {
  const [viewsList, setViewsList] = useState([])

  useEffect(() => {
    Axios.get("/timeline/master/list/view")
      .then(res => {
        if(res.data) {
          setViewsList(res.data)
          console.log(res.data);
        }
      })
  }, [])

  return (
    <MainDiv>
      <ViewsTop viewsList={viewsList} />
    </MainDiv>
  )
}

export default Main
