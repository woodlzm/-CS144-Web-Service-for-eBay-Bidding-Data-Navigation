Project 5

Q1
Answer: I am using SSL for (4)->(5) and (5)->(6). But since I used sendRedirect in the servlet at (3) so as to encrypt (4)->(5), (3)->(4) is also using SSL.

Q2
Answer: At the time the servlet gets the information of a specific item, it will add its information including item ID, name and buy price to the session. When the user clicks “Buy Now”, the corresponding servlet will get the session via item ID in the parameter and display corresponding contents.