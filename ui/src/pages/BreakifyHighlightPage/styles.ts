import styled from 'styled-components';


type ContainerProps = {
    size: "small" | "large";
}

export const Container = styled.div<ContainerProps>`

  text-align: center;

  ${({size}) => (size === "small" ? `
        font-size: 5rem;
    ` : `
        font-size: 7rem;
    `)}
  
  .highlight {
    background-color: ${props => props.theme.primary.background.color};
  }
  
  .letter {
    padding: 5px;
  }
`;


