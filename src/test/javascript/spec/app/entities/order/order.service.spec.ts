import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderService } from 'app/entities/order/order.service';
import { IOrder, Order } from 'app/shared/model/order.model';

describe('Service Tests', () => {
  describe('Order Service', () => {
    let injector: TestBed;
    let service: OrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrder;
    let expectedResult: IOrder | IOrder[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrderService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Order(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        currentDate,
        false,
        false,
        0,
        0,
        false,
        false,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Order', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            entryDate: currentDate,
            expectedDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Order()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Order', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            externalId: 'BBBBBB',
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            priority: 1,
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            isRushOrder: true,
            needsInventoryIssuance: true,
            remainingSubTotal: 1,
            grandTotal: 1,
            hasRateContract: true,
            gotQuoteFromVendors: true,
            vendorReason: 'BBBBBB',
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceResp: 'BBBBBB',
            transportResp: 'BBBBBB',
            unloadingResp: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            entryDate: currentDate,
            expectedDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Order', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            externalId: 'BBBBBB',
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            priority: 1,
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            isRushOrder: true,
            needsInventoryIssuance: true,
            remainingSubTotal: 1,
            grandTotal: 1,
            hasRateContract: true,
            gotQuoteFromVendors: true,
            vendorReason: 'BBBBBB',
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceResp: 'BBBBBB',
            transportResp: 'BBBBBB',
            unloadingResp: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            entryDate: currentDate,
            expectedDeliveryDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Order', () => {
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
