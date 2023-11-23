import React from "react";
import {Container} from "./styles";
import {useAppSelector} from "../../state/hooks/useAppSelector";
import {Highlight, selectHighlight} from "../BreakifyPage/highlighSlice";

type BreakifyHighlightPageProps = {
    size: "small" | "large";
    field: keyof Highlight;
}

const BreakifyHighlightPage: React.FC<BreakifyHighlightPageProps> = ({
                                                                         size = "small",
                                                                         field
                                                                     }) => {

    const highlightWord = useAppSelector(selectHighlight)[field];

    const indexHasToHighlight = (index: number) => {
        const found = highlightWord.highlights.find((highlight) => {
            return index === highlight.startIndex || (index > highlight.startIndex && index < highlight.endIndex);
        });
        return !!found;
    }

    return (
        <Container size={size}>
            {
                highlightWord.word.split("").map((letter, index) => {
                    return (
                        <span key={index} className={`letter ${indexHasToHighlight(index) ? "highlight" : ""}`}>{letter}</span>
                    )
                })
            }
        </Container>
    )
}


export default BreakifyHighlightPage;