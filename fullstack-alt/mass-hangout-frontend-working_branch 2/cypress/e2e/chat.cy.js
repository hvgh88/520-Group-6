
describe('Chat Page Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/chat'); 
  });

  it('should display the chat page correctly', () => {
    cy.get('.chat-sidebar').should('exist');
    cy.get('.chat-main').should('exist');
    cy.get('.search-container input').should('exist');
  });

  it('should display a placeholder when no chat is selected', () => {
    cy.get('.no-chat-selected').contains('Select a chat to start messaging').should('exist');
  });
});
