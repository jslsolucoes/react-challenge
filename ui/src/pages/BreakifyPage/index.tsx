import React from "react";
import {Container} from "./styles";
import Button from "../../components/Button";
import InputText from "../../components/InputText";
import {useAppDispatch} from "../../state/hooks/useAppDispatch";
import {createNewHighlight} from "./highlighSlice";
import BreakifyHighlightPage from "../BreakifyHighlightPage";
import {alertBox} from "../../components/alertBox";

type BreakifyForm = {
    firstName: string;
    lastName: string;
}

const BreakifyPage: React.FC = () => {

    const dispatch = useAppDispatch();

    const [form, setForm] = React.useState<BreakifyForm>({
        firstName: "",
        lastName: ""
    });

    const handleOnKeyUp = (name: string, value: string, key: string) => {

        if (key === "Enter") {
            highlight();
            return;
        }

        setForm({
            ...form,
            [name]: value
        });
    }

    const highlight = async () => {

        const {firstName, lastName} = form;
        if (!firstName || !lastName) {
            await alertBox.error("Missing fields", "Please fill first name and last name before Breakify");
            return;
        }
        dispatch(createNewHighlight({firstName, lastName}));
    }

    return (
        <Container>
            <div>
                <div>
                    <BreakifyHighlightPage size="large" field="firstName"/>
                    <BreakifyHighlightPage size="small" field="lastName"/>
                </div>
                <form className="form" noValidate={true}>
                    <div className="horizontal">
                        <div className="form-field">
                            <label htmlFor="firstName">First Name</label>
                            <InputText id="firstName" name="firstName" placeholder="Enter your first name"
                                       onKeyUp={handleOnKeyUp}/>
                        </div>
                        <div className="form-field">
                            <label htmlFor="lastName">Last Name</label>
                            <InputText id="lastName" name="lastName" placeholder="Enter your last name"
                                       onKeyUp={handleOnKeyUp}/>
                        </div>
                    </div>
                    <div>
                        <Button id="breakify" className="w-100" text="Breakify" onClick={highlight}/>
                    </div>
                </form>
            </div>
        </Container>
    )
}

export default BreakifyPage;