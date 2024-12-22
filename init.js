db = db.getSiblingDB("catalogdb");

db.books.insertOne({
    _id: '67683a6987029a969c2fe97d',
    name: 'Libro02 new',
    description: 'Description of Libro02 new',
    code: 'L1005',
    imgUri: 'https://img.pikbest.com/templates/20241024/corporate-and-unique-premium-vector-business-book-cover-design-ai_10997970.jpg!w700wp',
    active: true,
    price: '150.99',
    _class: 'org.prd.catalogservice.model.entity.BookEntity'
});