import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WorkEffortInventoryAssignService } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign.service';
import { IWorkEffortInventoryAssign, WorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

describe('Service Tests', () => {
  describe('WorkEffortInventoryAssign Service', () => {
    let injector: TestBed;
    let service: WorkEffortInventoryAssignService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkEffortInventoryAssign;
    let expectedResult: IWorkEffortInventoryAssign | IWorkEffortInventoryAssign[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkEffortInventoryAssignService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new WorkEffortInventoryAssign(0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkEffortInventoryAssign', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new WorkEffortInventoryAssign()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkEffortInventoryAssign', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkEffortInventoryAssign', () => {
        const returnedFromService = Object.assign(
          {
            quantity: 1,
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

      it('should delete a WorkEffortInventoryAssign', () => {
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
