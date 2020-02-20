# LeisurePass
Demo of a tourism pass management solution

## Prerequisites
* A running MySQL instance with the database `leisure_pass` created

## Building the application
Application can be built by running `gradle build`

## Running the application
Application can be run by executing `gradle bootRun`

Or alternatively from an IDE run the LeisurePassApplication#main method

By default the application will then be accessible at http://localhost:9999

## Future Extensions
* Liquibase database management
* An auth system such that the endpoints can only be called by authorised customers/vendors
* Additional lookup endpoints to retrieve IDs from customer/vendor/pass information
* Swagger for API doc generation
* A web UI
* An App UI
* CI/CD with github

## Available endpoints
### /customer/new
| Parameter    | Type   | Description |
|--------------|--------|-------------|
| customerName | String | Name of the customer |
| homeCity     | String | Home city of the customer |

Returns the details of the created customer

### /customer/{customerId}
Returns the customer details for the provided customerId

### /vendor/new
| Parameter    | Type   | Description |
|--------------|--------|-------------|
| vendorName   | String | Name of the vendor |

Returns the details of the vendor created

### /vendor/{vendorId}
Returns the vendor details for the provided vendorId

### /pass/new
| Parameter    | Type   | Description |
|--------------|--------|-------------|
| vendorId | String | Id of the vendor |
| customerId     | Long | Id of the customer |
| passCity | String | Location of the attraction |
| validFrom | Long | Epoch seconds of Midnight (UTC) of the day the pass is valid from |
| durationDays | Integer | Duration of the pass in days |

Returns the details of the pass that is generated

### /pass/{passId}/{customerId}/renew
Renews a valid or expired pass such that its validFrom now starts from midnight (UTC) today

Returns the details of the renewed pass

### /pass/{passId}/{vendorId}/validate
Validate whether a pass is valid or expired

Returns true if the pass is valid, along with the passId and vendor Id or false if the pass has expired

### /pass/{passId}/{customerId}/cancel
Cancel a customer's pass

If a valid or expired pass is found for the provided details it will be removed from the system 