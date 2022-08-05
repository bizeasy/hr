import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PaymentGatewayResponseService } from 'app/entities/payment-gateway-response/payment-gateway-response.service';
import { IPaymentGatewayResponse, PaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

describe('Service Tests', () => {
  describe('PaymentGatewayResponse Service', () => {
    let injector: TestBed;
    let service: PaymentGatewayResponseService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentGatewayResponse;
    let expectedResult: IPaymentGatewayResponse | IPaymentGatewayResponse[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentGatewayResponseService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PaymentGatewayResponse(0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PaymentGatewayResponse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PaymentGatewayResponse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PaymentGatewayResponse', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            referenceNumber: 'BBBBBB',
            gatewayMessage: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PaymentGatewayResponse', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            referenceNumber: 'BBBBBB',
            gatewayMessage: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentGatewayResponse', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
