import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderStatusService } from 'app/entities/order-status/order-status.service';
import { IOrderStatus, OrderStatus } from 'app/shared/model/order-status.model';

describe('Service Tests', () => {
  describe('OrderStatus Service', () => {
    let injector: TestBed;
    let service: OrderStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderStatus;
    let expectedResult: IOrderStatus | IOrderStatus[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrderStatusService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderStatus(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            statusDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OrderStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            statusDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            statusDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new OrderStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrderStatus', () => {
        const returnedFromService = Object.assign(
          {
            statusDateTime: currentDate.format(DATE_TIME_FORMAT),
            sequenceNo: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            statusDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OrderStatus', () => {
        const returnedFromService = Object.assign(
          {
            statusDateTime: currentDate.format(DATE_TIME_FORMAT),
            sequenceNo: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            statusDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OrderStatus', () => {
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
