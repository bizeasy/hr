import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { IInvoice, Invoice } from 'app/shared/model/invoice.model';

describe('Service Tests', () => {
  describe('Invoice Service', () => {
    let injector: TestBed;
    let service: InvoiceService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoice;
    let expectedResult: IInvoice | IInvoice[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvoiceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Invoice(0, currentDate, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            paidDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Invoice', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            paidDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            dueDate: currentDate,
            paidDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Invoice()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Invoice', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            paidDate: currentDate.format(DATE_TIME_FORMAT),
            invoiceMessage: 'BBBBBB',
            referenceNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            dueDate: currentDate,
            paidDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Invoice', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            paidDate: currentDate.format(DATE_TIME_FORMAT),
            invoiceMessage: 'BBBBBB',
            referenceNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            dueDate: currentDate,
            paidDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Invoice', () => {
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
