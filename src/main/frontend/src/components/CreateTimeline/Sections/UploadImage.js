import React from "react"
import Dropzone from "react-dropzone"
import { PlusOutlined } from "@ant-design/icons"
import styled from "styled-components"

const UploadImageDiv = styled.div`
  width: 100%;
  height: 140px;
  border: 1px solid lightgray;
  display: flex;
  align-items: center;
  justify-content: center;
`

const UploadImage = (props) => {

  return (
    <UploadImageDiv>
      <Dropzone onDrop={props.onDrop} multiple={false} maxSize={800000000}>
        {({ getRootProps, getInputProps }) => (
          <div
            {...getRootProps()}
          >
            <input {...getInputProps()} />
            <PlusOutlined />
          </div>
        )}
      </Dropzone>
    </UploadImageDiv>
  )
}

export default UploadImage
