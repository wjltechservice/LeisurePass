# LeisurePass
Demo of a tourism pass management solution

## Prerequisites
* A running MySQL instance with the database `leisure_pass` created

## Building the application
Application can be built by running `gradlew build`

## Running the application
Application can be run by executing `gradlew bootRun`

Or alternatively from an IDE run the LeisurePassApplication#main method

By default the application will then be accessible at http://localhost:9999

## Assumptions
* Security/authorisation has been handled by a prior system
* The required IDs will be known and provided by the UI, this is to simplify the number of endpoints needed. A possible
extension would be to provide extra endpoints to lookup IDs from customer/vendor/pass information

## Extensions
* I would have liked to manage the database with liquibase, but ran out of time to implement this
* An auth system such that the endpoints can only be called by authorised customers/vendors
* Additional lookup endpoints to retrieve IDs from customer/vendor/pass information

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