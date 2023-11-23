import React from 'react';
import {render} from '@testing-library/react';
import {withThemeProvider} from "./theme";
import BreakifyPage from "../pages/BreakifyPage";
import {getById} from "./selectors";
import {withProvider} from "./state";


test('renders a breakify page', () => {
    const view = render(withProvider(withThemeProvider(<BreakifyPage/>)));
    const firstNameElement = getById(view.container, "firstName");
    const lastNameElement = getById(view.container, "lastName");
    const buttonElement = getById(view.container, "breakify");
    expect(firstNameElement).toBeInTheDocument();
    expect(lastNameElement).toBeInTheDocument();
    expect(buttonElement).toBeInTheDocument();
});